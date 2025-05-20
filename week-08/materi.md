
# Dependency injection with Hilt

Hilt is a dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project. Doing manual dependency injection requires you to construct every class and its dependencies by hand, and to use containers to reuse and manage dependencies.

Hilt provides a standard way to use DI in your application by providing containers for every Android class in your project and managing their lifecycles automatically. Hilt is built on top of the popular DI library Dagger to benefit from the compile-time correctness, runtime performance, scalability, and Android Studio support that Dagger provides. For more information, see [Hilt and Dagger](https://developer.android.com/training/dependency-injection/hilt-cheatsheet).

---

## Adding dependencies

First, add the `hilt-android-gradle-plugin` plugin to your project's root `build.gradle` file:

<details>
<summary>Groovy</summary>

```groovy
plugins {
  ...
  id 'com.google.dagger.hilt.android' version '2.56.2' apply false
}
```
</details>

<details>
<summary>Kotlin</summary>

```kotlin
plugins {
  ...
  id("com.google.dagger.hilt.android") version "2.56.2" apply false
}
```
</details>

Then, apply the Gradle plugin and add these dependencies in your `app/build.gradle` file:

<details>
<summary>Groovy</summary>

```groovy
plugins {
  id 'com.google.devtools.ksp'
  id 'com.google.dagger.hilt.android'
}

android {
  ...
}

dependencies {
  implementation "com.google.dagger:hilt-android:2.56.2"
  ksp "com.google.dagger:hilt-compiler:2.56.2"
}
```
</details>

<details>
<summary>Kotlin</summary>

```kotlin
plugins {
  id("com.google.devtools.ksp")
  id("com.google.dagger.hilt.android")
}

android {
  ...
}

dependencies {
  implementation("com.google.dagger:hilt-android:2.56.2")
  ksp("com.google.dagger:hilt-android-compiler:2.56.2")
}
```
</details>

> **Note:** Projects that use both Hilt and data binding require Android Studio 4.0 or higher.  
> Hilt uses Java 8 features. To enable Java 8 in your project, add the following to the `app/build.gradle` file:

<details>
<summary>Groovy</summary>

```groovy
android {
  ...
  compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8
  }
}
```
</details>

<details>
<summary>Kotlin</summary>

```kotlin
android {
  ...
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
}
```
</details>

---

## Hilt application class

All apps that use Hilt must contain an `Application` class that is annotated with `@HiltAndroidApp`:

<details>
<summary>Kotlin</summary>

```kotlin
@HiltAndroidApp
class ExampleApplication : Application() { ... }
```
</details>

<details>
<summary>Java</summary>

```java
@HiltAndroidApp
public class ExampleApplication extends Application { ... }
```
</details>

The `@HiltAndroidApp` annotation triggers Hilt's code generation, including a base class for your application that serves as the application-level dependency container.

---

## Inject dependencies into Android classes

Once Hilt is set up in your Application class and an application-level component is available, Hilt can provide dependencies to other Android classes that have the `@AndroidEntryPoint` annotation:

<details>
<summary>Kotlin</summary>

```kotlin
@AndroidEntryPoint
class ExampleActivity : AppCompatActivity() { ... }
```
</details>

<details>
<summary>Java</summary>

```java
@AndroidEntryPoint
public class ExampleActivity extends AppCompatActivity { ... }
```
</details>

Hilt currently supports the following Android classes:

- Application (by using `@HiltAndroidApp`)
- ViewModel (by using `@HiltViewModel`)
- Activity
- Fragment
- View
- Service
- BroadcastReceiver

> **Note:** If you annotate an Android class with `@AndroidEntryPoint`, you must also annotate Android classes that depend on it. For example, if you annotate a fragment, then you must annotate any activities where you use that fragment.  
> Hilt only supports activities that extend `ComponentActivity`, such as `AppCompatActivity`.  
> Hilt only supports fragments that extend `androidx.Fragment`.  
> Hilt does not support retained fragments.

The `@AndroidEntryPoint` annotation generates an individual Hilt component for each Android class in your project. These components can receive dependencies from their respective parent classes as described in [Component hierarchy](#Component-hierarchy).

To obtain dependencies from a component, use the `@Inject` annotation to perform field injection:

<details>
<summary>Kotlin</summary>

```kotlin
@AndroidEntryPoint
class ExampleActivity : AppCompatActivity() {

  @Inject lateinit var analytics: AnalyticsAdapter
  ...
}
```
</details>

<details>
<summary>Java</summary>

```java
@AndroidEntryPoint
public class ExampleActivity extends AppCompatActivity {

  @Inject AnalyticsAdapter analytics;
  ...
}
```
</details>

> **Note:** Fields injected by Hilt cannot be private. Attempting to inject a private field with Hilt results in a compilation error.  
> Classes that Hilt injects can have other base classes that also use injection. Those classes don't need the `@AndroidEntryPoint` annotation if they're abstract.  
> To learn more about which lifecycle callback an Android class gets injected in, see [Component lifetimes](#Component-lifetimes).

---

## Define Hilt bindings

To perform field injection, Hilt needs to know how to provide instances of the necessary dependencies from the corresponding component. A binding contains the information necessary to provide instances of a type as a dependency.

### Constructor injection

One way to provide binding information to Hilt is constructor injection. Use the `@Inject` annotation on the constructor of a class to tell Hilt how to provide instances of that class:

<details>
<summary>Kotlin</summary>

```kotlin
class AnalyticsAdapter @Inject constructor(
  private val service: AnalyticsService
) { ... }
```
</details>

<details>
<summary>Java</summary>

```java
public class AnalyticsAdapter {
  @Inject
  public AnalyticsAdapter(AnalyticsService service) { ... }
}
```
</details>

The parameters of an annotated constructor of a class are the dependencies of that class. In the example, `AnalyticsAdapter` has `AnalyticsService` as a dependency. Therefore, Hilt must also know how to provide instances of `AnalyticsService`.

> **Note:** At build time, Hilt generates Dagger components for Android classes. Then, Dagger walks through your code and performs the following steps:  
> 1. Builds and validates dependency graphs, ensuring that there are no unsatisfied dependencies and no dependency cycles.  
> 2. Generates the classes that it uses at runtime to create the actual objects and their dependencies.

---

## Hilt modules

Sometimes a type cannot be constructor-injected. This can happen for multiple reasons. For example, you cannot constructor-inject an interface. You also cannot constructor-inject a type that you do not own, such as a class from an external library. In these cases, you can provide Hilt with binding information by using Hilt modules.

A Hilt module is a class that is annotated with `@Module`. Like a Dagger module, it informs Hilt how to provide instances of certain types. Unlike Dagger modules, you must annotate Hilt modules with `@InstallIn` to tell Hilt which Android class each module will be used or installed in.

> **Note:** Hilt modules are different from Gradle modules.  
> Dependencies that you provide in Hilt modules are available in all generated components that are associated with the Android class where you install the Hilt module.  
> Because Hilt's code generation needs access to all of the Gradle modules that use Hilt, the Gradle module that compiles your Application class also needs to have all of your Hilt modules and constructor-injected classes in its transitive dependencies.

---

### Inject interface instances with @Binds

Consider the `AnalyticsService` example. If `AnalyticsService` is an interface, then you cannot constructor-inject it. Instead, provide Hilt with the binding information by creating an abstract function annotated with `@Binds` inside a Hilt module.

The `@Binds` annotation tells Hilt which implementation to use when it needs to provide an instance of an interface.

The annotated function provides the following information to Hilt:
- The function return type tells Hilt what interface the function provides instances of.
- The function parameter tells Hilt which implementation to provide.

<details>
<summary>Kotlin</summary>

```kotlin
interface AnalyticsService {
  fun analyticsMethods()
}

// Constructor-injected, because Hilt needs to know how to
// provide instances of AnalyticsServiceImpl, too.
class AnalyticsServiceImpl @Inject constructor(
  ...
) : AnalyticsService { ... }

@Module
@InstallIn(ActivityComponent::class)
abstract class AnalyticsModule {

  @Binds
  abstract fun bindAnalyticsService(
    analyticsServiceImpl: AnalyticsServiceImpl
  ): AnalyticsService
}
```
</details>

<details>
<summary>Java</summary>

```java
public interface AnalyticsService {
  void analyticsMethods();
}

// Constructor-injected, because Hilt needs to know how to
// provide instances of AnalyticsServiceImpl, too.
public class AnalyticsServiceImpl implements AnalyticsService {
  @Inject
  public AnalyticsServiceImpl(...) { ... }
}

@Module
@InstallIn(ActivityComponent.class)
public abstract class AnalyticsModule {
  @Binds
  public abstract AnalyticsService bindAnalyticsService(
    AnalyticsServiceImpl analyticsServiceImpl
  );
}
```
</details>

The Hilt module `AnalyticsModule` is annotated with `@InstallIn(ActivityComponent.class)` because you want Hilt to inject that dependency into `ExampleActivity`. This annotation means that all of the dependencies in `AnalyticsModule` are available in all of the app's activities.

---

### Inject instances with @Provides

Interfaces are not the only case where you cannot constructor-inject a type. Constructor injection is also not possible if you don't own the class because it comes from an external library (classes like Retrofit, OkHttpClient, or Room databases), or if instances must be created with the builder pattern.

In these cases, you can tell Hilt how to provide instances of this type by creating a function inside a Hilt module and annotating that function with `@Provides`.

The annotated function supplies the following information to Hilt:
- The function return type tells Hilt what type the function provides instances of.
- The function parameters tell Hilt the dependencies of the corresponding type.
- The function body tells Hilt how to provide an instance of the corresponding type. Hilt executes the function body every time it needs to provide an instance of that type.

<details>
<summary>Kotlin</summary>

```kotlin
@Module
@InstallIn(ActivityComponent::class)
object AnalyticsModule {

  @Provides
  fun provideAnalyticsService(
    // Potential dependencies of this type
  ): AnalyticsService {
      return Retrofit.Builder()
               .baseUrl("https://example.com")
               .build()
               .create(AnalyticsService::class.java)
  }
}
```
</details>

<details>
<summary>Java</summary>

```java
@Module
@InstallIn(ActivityComponent.class)
public class AnalyticsModule {
  @Provides
  public static AnalyticsService provideAnalyticsService(
    // Potential dependencies of this type
  ) {
      return new Retrofit.Builder()
               .baseUrl("https://example.com")
               .build()
               .create(AnalyticsService.class);
  }
}
```
</details>

---

## Provide multiple bindings for the same type

In cases where you need Hilt to provide different implementations of the same type as dependencies, you must provide Hilt with multiple bindings. You can define multiple bindings for the same type with qualifiers.

A qualifier is an annotation that you use to identify a specific binding for a type when that type has multiple bindings defined.

First, define the qualifiers that you will use to annotate the `@Binds` or `@Provides` methods:

<details>
<summary>Kotlin</summary>

```kotlin
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OtherInterceptorOkHttpClient
```
</details>

<details>
<summary>Java</summary>

```java
@Qualifier
@Retention(RetentionPolicy.BINARY)
public @interface AuthInterceptorOkHttpClient {}

@Qualifier
@Retention(RetentionPolicy.BINARY)
public @interface OtherInterceptorOkHttpClient {}
```
</details>

Then, Hilt needs to know how to provide an instance of the type that corresponds with each qualifier. In this case, you could use a Hilt module with `@Provides`. Both methods have the same return type, but the qualifiers label them as two different bindings:

<details>
<summary>Kotlin</summary>

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @AuthInterceptorOkHttpClient
  @Provides
  fun provideAuthInterceptorOkHttpClient(
    authInterceptor: AuthInterceptor
  ): OkHttpClient {
      return OkHttpClient.Builder()
               .addInterceptor(authInterceptor)
               .build()
  }

  @OtherInterceptorOkHttpClient
  @Provides
  fun provideOtherInterceptorOkHttpClient(
    otherInterceptor: OtherInterceptor
  ): OkHttpClient {
      return OkHttpClient.Builder()
               .addInterceptor(otherInterceptor)
               .build()
  }
}
```
</details>

<details>
<summary>Java</summary>

```java
@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {

  @AuthInterceptorOkHttpClient
  @Provides
  public static OkHttpClient provideAuthInterceptorOkHttpClient(
    AuthInterceptor authInterceptor
  ) {
      return new OkHttpClient.Builder()
               .addInterceptor(authInterceptor)
               .build();
  }

  @OtherInterceptorOkHttpClient
  @Provides
  public static OkHttpClient provideOtherInterceptorOkHttpClient(
    OtherInterceptor otherInterceptor
  ) {
      return new OkHttpClient.Builder()
               .addInterceptor(otherInterceptor)
               .build();
  }
}
```
</details>

You can inject the specific type that you need by annotating the field or parameter with the corresponding qualifier:

<details>
<summary>Kotlin</summary>

```kotlin
// As a dependency of another class.
@Module
@InstallIn(ActivityComponent::class)
object AnalyticsModule {

  @Provides
  fun provideAnalyticsService(
    @AuthInterceptorOkHttpClient okHttpClient: OkHttpClient
  ): AnalyticsService {
      return Retrofit.Builder()
               .baseUrl("https://example.com")
               .client(okHttpClient)
               .build()
               .create(AnalyticsService::class.java)
  }
}

// As a dependency of a constructor-injected class.
class ExampleServiceImpl @Inject constructor(
  @AuthInterceptorOkHttpClient private val okHttpClient: OkHttpClient
) : ...

// At field injection.
@AndroidEntryPoint
class ExampleActivity: AppCompatActivity() {

  @AuthInterceptorOkHttpClient
  @Inject lateinit var okHttpClient: OkHttpClient
}
```
</details>

<details>
<summary>Java</summary>

```java
// As a dependency of another class.
@Module
@InstallIn(ActivityComponent.class)
public class AnalyticsModule {

  @Provides
  public static AnalyticsService provideAnalyticsService(
    @AuthInterceptorOkHttpClient OkHttpClient okHttpClient
  ) {
      return new Retrofit.Builder()
               .baseUrl("https://example.com")
               .client(okHttpClient)
               .build()
               .create(AnalyticsService.class);
  }
}

// As a dependency of a constructor-injected class.
public class ExampleServiceImpl {
  @Inject
  public ExampleServiceImpl(
    @AuthInterceptorOkHttpClient OkHttpClient okHttpClient
  ) { ... }
}

// At field injection.
@AndroidEntryPoint
public class ExampleActivity extends AppCompatActivity {

  @AuthInterceptorOkHttpClient
  @Inject OkHttpClient okHttpClient;
}
```
</details>

> **Best practice:** If you add a qualifier to a type, add qualifiers to all the possible ways to provide that dependency. Leaving the base or common implementation without a qualifier is error-prone and could result in Hilt injecting the wrong dependency.

---

## Predefined qualifiers in Hilt

Hilt provides some predefined qualifiers. For example, as you might need the `Context` class from either the application or the activity, Hilt provides the `@ApplicationContext` and `@ActivityContext` qualifiers.

Suppose that the `AnalyticsAdapter` class from the example needs the context of the activity. The following code demonstrates how to provide the activity context to `AnalyticsAdapter`:

<details>
<summary>Kotlin</summary>

```kotlin
class AnalyticsAdapter @Inject constructor(
    @ActivityContext private val context: Context,
    private val service: AnalyticsService
) { ... }
```
</details>

<details>
<summary>Java</summary>

```java
public class AnalyticsAdapter {
  @Inject
  public AnalyticsAdapter(
    @ActivityContext Context context,
    AnalyticsService service
  ) { ... }
}
```
</details>

For other predefined bindings available in Hilt, see [Component default bindings](#Component-default-bindings).

---

## Generated components for Android classes

For each Android class in which you can perform field injection, there's an associated Hilt component that you can refer to in the `@InstallIn` annotation. Each Hilt component is responsible for injecting its bindings into the corresponding Android class.

The following table lists Hilt components and the classes they inject:

| Hilt component           | Injects for         |
| ------------------------ | -------------------- |
| `SingletonComponent`     | Application          |
| `ActivityRetainedComponent` | N/A               |
| `ViewModelComponent`     | ViewModel            |
| `ActivityComponent`      | Activity             |
| `FragmentComponent`      | Fragment             |
| `ViewComponent`          | View                 |
| `ViewWithFragmentComponent` | View annotated with `@WithFragmentBindings` |
| `ServiceComponent`       | Service              |

> **Note:** Hilt doesn't generate a component for broadcast receivers because Hilt injects broadcast receivers directly from `SingletonComponent`.

---

## Component lifetimes

Hilt automatically creates and destroys instances of generated component classes following the lifecycle of the corresponding Android classes.

| Generated component         | Created at                | Destroyed at              |
| --------------------------- | ------------------------- | ------------------------- |
| `SingletonComponent`        | Application#onCreate()    | Application destroyed     |
| `ActivityRetainedComponent` | Activity#onCreate()       | Activity#onDestroy()      |
| `ViewModelComponent`        | ViewModel created         | ViewModel destroyed       |
| `ActivityComponent`         | Activity#onCreate()       | Activity#onDestroy()      |
| `FragmentComponent`         | Fragment#onAttach()       | Fragment#onDestroy()      |
| `ViewComponent`             | View#super()              | View destroyed            |
| `ViewWithFragmentComponent` | View#super()              | View destroyed            |
| `ServiceComponent`          | Service#onCreate()        | Service#onDestroy()       |

> **Note:** `ActivityRetainedComponent` lives across configuration changes, so it is created at the first `Activity#onCreate()` and destroyed at the last `Activity#onDestroy()`.

---

## Component scopes

By default, all bindings in Hilt are unscoped. This means that each time your app requests the binding, Hilt creates a new instance of the needed type.

However, Hilt also allows a binding to be scoped to a particular component. Hilt only creates a scoped binding once per instance of the component that the binding is scoped to, and all requests for that binding share the same instance.

The table below lists scope annotations for each generated component:

| Android class | Generated component         | Scope                   |
| ------------- | --------------------------- | ----------------------- |
| Application   | `SingletonComponent`        | `@Singleton`            |
| Activity      | `ActivityRetainedComponent` | `@ActivityRetainedScoped` |
| ViewModel     | `ViewModelComponent`        | `@ViewModelScoped`      |
| Activity      | `ActivityComponent`         | `@ActivityScoped`       |
| Fragment      | `FragmentComponent`         | `@FragmentScoped`       |
| View          | `ViewComponent`             | `@ViewScoped`           |
| View annotated with `@WithFragmentBindings` | `ViewWithFragmentComponent` | `@ViewScoped` |
| Service       | `ServiceComponent`          | `@ServiceScoped`        |

> **Note:** Scoping a binding to a component can be costly because the provided object stays in memory until that component is destroyed. Minimize the use of scoped bindings in your application. It is appropriate to use component-scoped bindings for bindings with an internal state that requires that same instance to be used within a certain scope, for bindings that need synchronization, or for bindings that you have measured to be expensive to create.

Example of scoping `AnalyticsAdapter` to `ActivityComponent`:

<details>
<summary>Kotlin</summary>

```kotlin
@ActivityScoped
class AnalyticsAdapter @Inject constructor(
  private val service: AnalyticsService
) { ... }
```
</details>

> **Note:** If you need a binding to be available across the entire app, scope it to `SingletonComponent`. For example:

<details>
<summary>Kotlin</summary>

```kotlin
// If AnalyticsService is an interface.
@Module
@InstallIn(SingletonComponent::class)
abstract class AnalyticsModule {

  @Singleton
  @Binds
  abstract fun bindAnalyticsService(
    analyticsServiceImpl: AnalyticsServiceImpl
  ): AnalyticsService
}

// If you don't own AnalyticsService.
@Module
@InstallIn(SingletonComponent::class)
object AnalyticsModule {

  @Singleton
  @Provides
  fun provideAnalyticsService(): AnalyticsService {
      return Retrofit.Builder()
               .baseUrl("https://example.com")
               .build()
               .create(AnalyticsService::class.java)
  }
}
```
</details>

---

## Component hierarchy

Installing a module into a component allows its bindings to be accessed as a dependency of other bindings in that component or in any child component below it in the component hierarchy:

```
ViewWithFragmentComponent is under FragmentComponent.
FragmentComponent and ViewComponent are under ActivityComponent.
ActivityComponent is under ActivityRetainedComponent.
ViewModelComponent is under ActivityRetainedComponent.
ActivityRetainedComponent and ServiceComponent are under SingletonComponent.
```

> **Note:** By default, if you perform field injection in a view, `ViewComponent` can use bindings that are defined in the `ActivityComponent`. If you also need to use bindings that are defined in `FragmentComponent` and the view is part of a fragment, use the `@WithFragmentBindings` annotation with `@AndroidEntryPoint`.

---

## Component default bindings

Each Hilt component comes with a set of default bindings that Hilt can inject as dependencies into your own custom bindings. Note that these bindings correspond to the general activity and fragment types and not to any specific subclass. This is because Hilt uses a single activity component definition to inject all activities. Each activity has a different instance of this component.

| Android component            | Default bindings               |
| ---------------------------- | ------------------------------ |
| `SingletonComponent`         | Application                    |
| `ActivityRetainedComponent`  | Application                    |
| `ViewModelComponent`         | SavedStateHandle               |
| `ActivityComponent`          | Application, Activity          |
| `FragmentComponent`          | Application, Activity, Fragment|
| `ViewComponent`              | Application, Activity, View    |
| `ViewWithFragmentComponent`  | Application, Activity, Fragment, View |
| `ServiceComponent`           | Application, Service           |

The application context binding is also available using `@ApplicationContext`. For example:

<details>
<summary>Kotlin</summary>

```kotlin
class AnalyticsServiceImpl @Inject constructor(
  @ApplicationContext context: Context
) : AnalyticsService { ... }
```
</details>

<details>
<summary>Java</summary>

```java
public class AnalyticsServiceImpl {
  @Inject
  public AnalyticsServiceImpl(@ApplicationContext Context context) { ... }
}
```
</details>

The activity context binding is also available using `@ActivityContext`. For example:

<details>
<summary>Kotlin</summary>

```kotlin
class AnalyticsAdapter @Inject constructor(
  @ActivityContext context: Context
) { ... }
```
</details>

<details>
<summary>Java</summary>

```java
public class AnalyticsAdapter {
  @Inject
  public AnalyticsAdapter(@ActivityContext Context context) { ... }
}
```
</details>

---

## Inject dependencies in classes not supported by Hilt

Hilt comes with support for the most common Android classes. However, you might need to perform field injection in classes that Hilt doesn't support.

In those cases, you can create an entry point using the `@EntryPoint` annotation. An entry point is the boundary between code that is managed by Hilt and code that is not. It is the point where code first enters into the graph of objects that Hilt manages. Entry points allow Hilt to use code that Hilt does not manage to provide dependencies within the dependency graph.

For example, Hilt doesn't directly support content providers. If you want a content provider to use Hilt to get some dependencies, you need to define an interface that is annotated with `@EntryPoint` for each binding type that you want and include qualifiers. Then add `@InstallIn` to specify the component in which to install the entry point as follows:

<details>
<summary>Kotlin</summary>

```kotlin
class ExampleContentProvider : ContentProvider() {

  @EntryPoint
  @InstallIn(SingletonComponent::class)
  interface ExampleContentProviderEntryPoint {
    fun analyticsService(): AnalyticsService
  }

  ...
}
```
</details>

<details>
<summary>Java</summary>

```java
public class ExampleContentProvider extends ContentProvider {

  @EntryPoint
  @InstallIn(SingletonComponent.class)
  public interface ExampleContentProviderEntryPoint {
    AnalyticsService analyticsService();
  }

  ...
}
```
</details>

To access an entry point, use the appropriate static method from `EntryPointAccessors`. The parameter should be either the component instance or the `@AndroidEntryPoint` object that acts as the component holder. Make sure that the component you pass as a parameter and the `EntryPointAccessors` static method both match the Android class in the `@InstallIn` annotation on the `@EntryPoint` interface:

<details>
<summary>Kotlin</summary>

```kotlin
class ExampleContentProvider: ContentProvider() {
    ...

  override fun query(...): Cursor {
    val appContext = context?.applicationContext ?: throw IllegalStateException()
    val hiltEntryPoint =
      EntryPointAccessors.fromApplication(appContext, ExampleContentProviderEntryPoint::class.java)

    val analyticsService = hiltEntryPoint.analyticsService()
    ...
  }
}
```
</details>

<details>
<summary>Java</summary>

```java
public class ExampleContentProvider extends ContentProvider {
    ...

  @Override
  public Cursor query(...) {
    Context appContext = getContext().getApplicationContext();
    ExampleContentProviderEntryPoint hiltEntryPoint =
      EntryPointAccessors.fromApplication(appContext, ExampleContentProviderEntryPoint.class);

    AnalyticsService analyticsService = hiltEntryPoint.analyticsService();
    ...
  }
}
```
</details>

> **Note:** In this example, you must use the application context to retrieve the entry point because the entry point is installed in `SingletonComponent`. If the binding that you wanted to retrieve were in the `ActivityComponent`, you would instead use the activity context.

---

## Hilt and Dagger

Hilt is built on top of the Dagger dependency injection library, providing a standard way to incorporate Dagger into an Android application.

With respect to Dagger, the goals of Hilt are as follows:
1. To simplify Dagger-related infrastructure for Android apps.
2. To create a standard set of components and scopes to ease setup, readability, and code sharing between apps.
3. To provide an easy way to provision different bindings to various build types, such as testing, debug, or release.

Because the Android operating system instantiates many of its own framework classes, using Dagger in an Android app requires you to write a substantial amount of boilerplate. Hilt reduces the boilerplate code that is involved in using Dagger in an Android application. Hilt automatically generates and provides the following:

- Components for integrating Android framework classes with Dagger that you would otherwise need to create by hand.
- Scope annotations to use with the components that Hilt generates automatically.
- Predefined bindings to represent Android classes such as Application or Activity.
- Predefined qualifiers to represent `@ApplicationContext` and `@ActivityContext`.

Dagger and Hilt code can coexist in the same codebase. However, in most cases it is best to use Hilt to manage all of your usage of Dagger on Android. To migrate a project that uses Dagger to Hilt, see the [migration guide](https://developer.android.com/training/dependency-injection/hilt-migration) and the [Migrating your Dagger app to Hilt codelab](https://developer.android.com/codelabs/hilt-migration).

---

## Additional resources

- [Dependency injection with Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- [Hilt and Dagger annotations cheat sheet](https://developer.android.com/training/dependency-injection/hilt-cheatsheet)
- [Hilt testing guide](https://developer.android.com/training/dependency-injection/hilt-testing)

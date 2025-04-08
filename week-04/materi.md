# 📚 Android UI Components in Jetpack Compose

## 1. Jetpack Compose 
Jetpack Compose is Android’s modern toolkit for building native UI. It provides a rich set of components that follow a declarative programming model.

---

## 2. Basic UI Components

### 📝 Text
Used to display text.
```kotlin
Text("Hello, Jetpack Compose!")
```

### 🔘 Button
A clickable button.
```kotlin
Button(onClick = { /* do something */ }) {
    Text("Click Me")
}
```

### 🖊️ OutlinedTextField
Text input with an outline.
```kotlin
OutlinedTextField(
    value = text,
    onValueChange = { text = it },
    label = { Text("Enter name") }
)
```

### 🖼️ Image
Displays an image.
```kotlin
Image(
    painter = painterResource(id = R.drawable.ic_launcher_foreground),
    contentDescription = "Sample Image"
)
```

### 📦 Card
Material container to group content.
```kotlin
Card(elevation = CardDefaults.cardElevation(8.dp)) {
    Text("Inside a card", modifier = Modifier.padding(16.dp))
}
```

---

## 3. Layout Containers

### 🔲 Column
Vertical arrangement.
```kotlin
Column {
    Text("Item 1")
    Text("Item 2")
}
```

### 🔳 Row
Horizontal arrangement.
```kotlin
Row {
    Text("Left")
    Text("Right")
}
```

### 📥 Box
Overlapping elements.
```kotlin
Box {
    Text("Bottom")
    Text("Top", modifier = Modifier.align(Alignment.Center))
}
```

### ↔️ Spacer
Adds space between elements.
```kotlin
Spacer(modifier = Modifier.height(16.dp))
```

---

## 4. Advanced UI Components

### 🧭 Scaffold
Base layout structure (top bar, bottom bar, etc).
```kotlin
Scaffold(
    topBar = {
        TopAppBar(title = { Text("App Title") })
    }
) { innerPadding ->
    Text("Content", modifier = Modifier.padding(innerPadding))
}
```

### 🧱 LazyColumn
Efficient vertical list.
```kotlin
LazyColumn {
    items(10) {
        Text("Item #$it")
    }
}
```

### 🧱 LazyRow
Efficient horizontal list.
```kotlin
LazyRow {
    items(5) {
        Text("Item #$it")
    }
}
```

### ✅ Checkbox
For boolean options.
```kotlin
Checkbox(
    checked = isChecked,
    onCheckedChange = { isChecked = it }
)
```

### 🔘 RadioButton
For single choice from a group.
```kotlin
RadioButton(
    selected = isSelected,
    onClick = { isSelected = true }
)
```

### 🧩 Switch
On/off toggle switch.
```kotlin
Switch(
    checked = isToggled,
    onCheckedChange = { isToggled = it }
)
```

### 🔢 Slider
Select a value from range.
```kotlin
Slider(
    value = sliderValue,
    onValueChange = { sliderValue = it },
    valueRange = 0f..100f
)
```

---

## 5. Modifiers
Modifiers are used to decorate or add behavior to UI components.
```kotlin
Text(
    "Styled Text",
    modifier = Modifier
        .padding(8.dp)
        .background(Color.Gray)
        .fillMaxWidth()
)
```

---

## 6. Preview
Use `@Preview` to see the composable in Android Studio without running the app.
```kotlin
@Preview(showBackground = true)
@Composable
fun PreviewGreeting() {
    Text("Hello Preview")
}
```

---

## 7. Conclusion
Mastering UI components in Jetpack Compose allows you to build beautiful, responsive, and modern Android interfaces with less code and more flexibility.

Practice using these components by building small UIs and gradually combining them into more complex layouts.

---

## 8. Resources
- [Jetpack Compose Docs](https://developer.android.com/jetpack/compose)
- [Compose Samples](https://github.com/android/compose-samples)
- [Material 3 Compose](https://developer.android.com/jetpack/compose/material3)

Happy Composing! 🎨📱


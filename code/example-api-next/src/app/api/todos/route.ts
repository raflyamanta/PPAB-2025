import { db } from "@/lib/database";

export const POST = async (request: Request) => {
  try {
    const json = await request.json();
    const { title, description } = json;
    const result = await db
      .insertInto("todos")
      .values({
        title,
        description,
        status: false,
      })
      .returning("id")
      .executeTakeFirst();

    return Response.json({ id: result?.id });
  } catch (e) {
    return new Response(undefined, {
      status: 500,
      statusText: "Terjadi kesalahan tak terduga pada server",
    });
  }
};

export const GET = async () => {
  try {
    const result = await db
      .selectFrom("todos")
      .select(["id", "title", "description", "status"])
      .orderBy("title")
      .execute();

    return Response.json({ todos: result });
  } catch (e) {
    return new Response(undefined, {
      status: 500,
      statusText: "Terjadi kesalahan tak terduga pada server",
    });
  }
};

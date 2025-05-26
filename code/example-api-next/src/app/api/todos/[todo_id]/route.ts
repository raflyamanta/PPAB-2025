import { db } from "@/lib/database";

interface Params {
  params: Promise<{ todo_id: string }>;
}

export const GET = async (_: Request, { params }: Params) => {
  try {
    const todoId = (await params).todo_id;

    const result = await db
      .selectFrom("todos")
      .selectAll()
      .where("id", "=", todoId as never)
      .executeTakeFirst();

    if (!result)
      return new Response(undefined, {
        status: 404,
        statusText: "Todo tidak ditemukan",
      });

    return Response.json({ todo: result });
  } catch (e) {
    console.log(e);
    return new Response(undefined, {
      status: 500,
      statusText: "Terjadi kesalahan tak terduga pada server",
    });
  }
};

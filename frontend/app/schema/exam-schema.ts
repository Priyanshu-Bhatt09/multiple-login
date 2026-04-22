//validation schema
import {z} from 'zod'; //zod is a ts first schema validation library

export const optionSchema = z.object({
    text: z.string(),
    isCorrect: z.boolean()
});

export const questionSchema = z.object({
    text: z.string(),
    points: z.number(),
    negativePoint: z.number(),
    options: z.array(optionSchema),
    paragraph: z.string(),
    type: z.enum([
        "MCQ",
        "MCA",
        "SHORT_TEXT",
        "CODING",
        "INPUT",
        "MATCH",
        "COMPREHENSION"
    ]),
    correctOption: z.union([z.number(), z.string()]).optional().nullable(), //this field accepts a string, a number, null or nothing at all.
    correctAnswer: z.string().optional().nullable(), //expects a string but don't throw an error if data is missing or null
    correctCode: z.string().optional().nullable()
});

export const examSchema = z.object({
    title: z.string(),
    questions: z.array(questionSchema)
});

export type ExamFormValues = z.infer<typeof examSchema>
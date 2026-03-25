import MCQ from "./types/mcq";

export function renderQuestionType(type: string, index: number) {
    switch(type) {
        case "MCQ":
            return <MCQ index={index} />;
        default:
            return null;
    }
}
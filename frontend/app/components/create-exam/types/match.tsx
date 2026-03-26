import { useFieldArray, useFormContext } from "react-hook-form"

export default function Match({index}: any) {
    const {register, control} = useFormContext();

    const {fields, append, remove} = useFieldArray({
        control,
        name: `questions.${index}.pairs`
    });
    return(
        <div>
            {fields.map((field, i) => (
                <div key={field.id} className="flex mb-2 gap-4">
                    <input 
                    {...register(`questions.${index}.pairs.${i}.left`)}
                    placeholder="Left item"
                    className="mx-5 my-2 border p-1"
                    />
                    <input 
                    {...register(`questions.${index}.pairs.${i}.right`)}
                    placeholder="Right item"
                    className="mx-5 my-2 border p-1"
                    />
                    <button type="button" onClick={() => remove(i)}>X</button>
                </div>
            ))}

            <button
            type="button"
            onClick={() => append({left: "", right: ""})}
            className="mx-5 my-1 border-2 rounded-sm p-1"
            >
                Add Pair
            </button>
        </div>
    )
}
import Link from "next/link";

const account = () => {
    return(
        <>
        <div className="bg-amber-100 min-h-screen flex justify-center items-center">
            <div className="flex flex-col border-2 h-[60vh] w-[30vw]">
                <input type="text" placeholder="Name"
                className="border-2 p-1 m-2 "
                />
                <input type="text" placeholder="Email"
                className="border-2 p-1 m-2 "
                />
                <input type="text" placeholder="Password"
                className="border-2 p-1 m-2 "
                />
                <input type="text" placeholder="Confirm-Password"
                className="border-2 p-1 m-2 "
                />
                <button className="bg-red-200 p-1 border rounded-sm m-2">Create Account</button>
                <h3 className="text-center">Or Already has an account then 
                    <Link href="/login" className="text-red-500 m-1">
                    login
                    </Link>
                </h3>
            </div>
        </div>
        </>
    )
}
export default account;
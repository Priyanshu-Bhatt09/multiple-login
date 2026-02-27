import Link from "next/link";
const login = () => {
    return(
        <>
        <div className="bg-amber-100 min-h-screen flex justify-center items-center">
            <div className="flex flex-col border-2 h-[60vh] w-[30vw]">
            <input type="text" placeholder="Email"
            className="border-2 p-1 m-2 "
            />
            <input type="password" placeholder="Password"
            className="border-2 p-1 m-2"
            />
            <button className="bg-red-200 p-1 border rounded-sm m-2">Login</button>
            <h3 className="text-center">OR</h3>
            <Link href="/create-acc" className="m-2">
            <button className="bg-red-200 p-1 border rounded-sm  w-full">Create Account</button>
            </Link>
            </div>
            
        </div>
        </>
    )
}
export default login;
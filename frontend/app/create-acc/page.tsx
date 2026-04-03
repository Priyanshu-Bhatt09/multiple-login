"use client"

import Link from "next/link";
import { useState } from "react";

const account = () => {
    const [email, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [name, setName] = useState<string>("");
    const [confirmPass, setConfirmPass] = useState<string>("");
    const [error, setError] = useState<string>("");

    const handleSignup = async() => {
        if(password !== confirmPass) {
            setError("Passwords do not match");
            return;
        }
        setError("");

        try {
            const res = await fetch("http://localhost:8080/proctor/login/signup", {
                method: "POST",
                headers: {
                    "Content-Type" : "application/json"
                },
                body: JSON.stringify({
                    email: email,
                    name: name,
                    password: password
                })
            });
            console.log("Signup successful");
        } catch(e) {
            console.error("Signup error: ", e);
        }
    }

    return(
        <>
        <div className="bg-amber-100 min-h-screen flex justify-center items-center">
            <div className="flex flex-col border-2 h-[60vh] w-[30vw]">
                <input type="text" placeholder="Name"
                className="border-2 p-1 m-2 "
                value={name}
                onChange={(e) => setName(e.target.value)}
                />
                <input type="text" placeholder="Email"
                className="border-2 p-1 m-2 "
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                />
                <input type="text" placeholder="Password"
                className="border-2 p-1 m-2 "
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                />
                <input type="text" placeholder="Confirm-Password"
                className="border-2 p-1 m-2 "
                value={confirmPass}
                onChange={(e) => setConfirmPass(e.target.value)}
                />
                {error && (
                    <p className="text-red-500 ml-2">{error}</p>
                )}
                <button className="bg-red-200 p-1 border rounded-sm m-2"
                onClick={handleSignup}
                >Create Account</button>
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
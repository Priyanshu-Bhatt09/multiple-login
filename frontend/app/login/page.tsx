"use client"

import Link from "next/link";
import { useState } from "react";

const login = () => {
    const [email, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const handleLogin = async() => {
        try {
            const response = await fetch("http://localhost:8080/proctor/login/signin", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    email: email,
                    password: password
                })
            });

            const data = await response.json();

            console.log("Login response: ", data);
            //save token
            localStorage.setItem("token", data.token);

            console.log("Login successful")
        } catch (error) {
            console.error("Login error", error);
        }
    };
    return(
        <>
        <div className="bg-amber-100 min-h-screen flex justify-center items-center">
            <div className="flex flex-col border-2 h-[60vh] w-[30vw]">
            <input type="text" placeholder="Email"
            className="border-2 p-1 m-2 "
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            />
            <input type="password" placeholder="Password"
            className="border-2 p-1 m-2"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            />
            <button className="bg-red-200 p-1 border rounded-sm m-2"
            onClick={handleLogin}
            >Login</button>
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
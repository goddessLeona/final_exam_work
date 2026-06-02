import { apiFetch } from "./api-fetch";

export async function login(
    data: LoginRequest
    ): Promise<LoginResponse> {

    const res = await apiFetch(
        `${process.env.NEXT_PUBLIC_API_URL}/auth/login`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
        credentials: "include",
    });

    if (!res.ok) {
        throw new Error("Invalid credentials");
    }

    return res.json();
}

export interface LoginRequest {
    username: string;
    password: string;
}

export interface LoginResponse {
    roles: string[];
}
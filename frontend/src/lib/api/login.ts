import { apiFetch } from "./api-fetch";
import { handleResponse } from "./handleResponse";

export async function login(
    data: LoginRequest
    ): Promise<LoginResponse> {

    const response = await apiFetch(
        `${process.env.NEXT_PUBLIC_API_URL}/auth/login`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
        credentials: "include",
    });

    return handleResponse(response);
}

export interface LoginRequest {
    username: string;
    password: string;
}

export interface LoginResponse {
    roles: string[];
}
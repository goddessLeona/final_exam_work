import { apiFetch } from "./api-fetch";
import { handleResponse } from "./handleResponse";

export async function logout(): Promise<void> {

    const response = await apiFetch(
        `${process.env.NEXT_PUBLIC_API_URL}/auth/logout`,
        {
            method: "POST",
            credentials: "include",
        }
    );

    return handleResponse(response);
}
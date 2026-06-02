import { apiFetch } from "./api-fetch";

export async function logout(): Promise<void> {

    const response = await apiFetch(
        `${process.env.NEXT_PUBLIC_API_URL}/auth/logout`,
        {
            method: "POST",
            credentials: "include",
        }
    );

    if (!response.ok) {
        throw new Error("Logout failed");
    }
}
export async function logout(): Promise<void> {

    const response = await fetch(
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
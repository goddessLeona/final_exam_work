
export async function apiFetch(
    url: string,
    options?: RequestInit
) {

    const response = await fetch(url, {
        ...options,
        credentials: "include",
    });

    if (response.status === 401) {

        window.location.href = "/login";

        throw new Error("Unauthorized");
    }

    return response;
}
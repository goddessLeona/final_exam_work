
export async function handleResponse<T>(
    response: Response
): Promise<T> {

    if (!response.ok) {

        let message = `Request failed (${response.status})`;

        try {
            const data = await response.json();
            message = data?.message || message;
        } catch {}

        const error = new Error(message) as Error & { status?: number };

        error.status = response.status;

        throw error;
    }

    // No content
    if (response.status === 204) {
        return null as T;
    }

    const contentType = response.headers.get("content-type");

    // JSON response
    if (contentType?.includes("application/json")) {
        return response.json();
    }

    // Plain text response
    return response.text() as unknown as T;
}
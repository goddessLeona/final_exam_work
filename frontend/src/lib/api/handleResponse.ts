
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

    // HANDLE EMPTY BODY
    const text = await response.text();

    return text ? JSON.parse(text) : null;
}
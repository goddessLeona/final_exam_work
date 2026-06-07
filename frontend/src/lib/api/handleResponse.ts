
export async function handleResponse<T>(
    response: Response
): Promise<T> {

    if (!response.ok) {

        let errorData: any = {};

        try {
            errorData = await response.json();
        } catch {
            try {
                errorData = await response.text();
            } catch {}
        }

        const error = new Error(
            errorData?.message || errorData || `Request failed (${response.status})`
        ) as Error & {
            status?: number;
            errors?: Record<string, string>;
        };

        error.status = response.status;
        error.errors = errorData?.errors;

        throw error;
    }

    if (response.status === 204) {
        return null as T;
    }

    const contentType = response.headers.get("content-type");

    if (contentType?.includes("application/json")) {
        return response.json();
    }

    return response.text() as unknown as T;
}

export function handleAuthError(err: any) {
    if (err?.status === 401 || err?.status === 403) {
        window.location.href = "/login";
        return true;
    }
    return false;
}
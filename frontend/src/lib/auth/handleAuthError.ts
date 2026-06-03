
export function handleAuthError(err: any) {

    if (
        err.message.includes("401") ||
        err.message.includes("403")
    ) {
        window.location.href = "/login";
        return true;
    }

    return false;
}
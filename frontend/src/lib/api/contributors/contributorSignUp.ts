import { apiFetch } from "../api-fetch";
import { handleResponse } from "../handleResponse";

export async function signUpContributor(
    data: SignUpRequest
    ) {

    const response = await apiFetch(
        `${process.env.NEXT_PUBLIC_API_URL}/user/signup-contributor`,
        {
            method: "POST",
            headers:{"Content-Type": "application/json"},
            body: JSON.stringify(data),
        }
    );

    return handleResponse(response);
}

export interface SignUpRequest {
    username: string;
    password: string;
    confirmPassword: string;
    email: string;
    firstName: string;
    lastName: string;
}
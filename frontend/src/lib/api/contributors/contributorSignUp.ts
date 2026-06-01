
export async function signUpContributor(
    data: SignUpRequest
    ) {

    const response = await fetch(
        `${process.env.NEXT_PUBLIC_API_URL}/user/signup-contributor`,
        {
            method: "POST",
            headers:{"Content-Type": "application/json"},
            body: JSON.stringify(data),
        }
    );

    const json = await response.json();

    if (!response.ok) {

        throw {
            message: json.message,
            errors: json.errors ?? null
        };
    }
    
    return json; 
}

export interface SignUpRequest {
    username: string;
    password: string;
    confirmPassword: string;
    email: string;
    firstName: string;
    lastName: string;
}
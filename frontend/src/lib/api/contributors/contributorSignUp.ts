
export async function signUpContributor(
    data: SignUpResponse
    ) {

    const response = await fetch(
        `${process.env.NEXT_PUBLIC_API_URL}/user/signup-contributor`,
        {
            method: "POST",
            headers:{"Content-Type": "application/json"},
            body: JSON.stringify(data),
        }
    );


    if (!response.ok) {
        let errorData;

        try {
            errorData = await response.json();
        } catch {
            throw { message : "UnKnown error"};
        }

        throw errorData;
    }

    return response.json; 
}

export interface SignUpResponse {
    username: string;
    password: string;
    confirmPassword: string;
    email: string;
    firstName: string;
    lastName: string;
    birthYear: number | null;
    birthMonth: number | null;
    birthDay: number | null;
}
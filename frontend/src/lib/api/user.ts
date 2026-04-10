
export interface signUpResponse {
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

export async function signUpContributor(data: signUpResponse) {
    const response = await fetch(
        "http://localhost:8080/user/signup-contributor",
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
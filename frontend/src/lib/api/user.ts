import { error } from "console";

export async function signUpContributor(data: any) {
    const response = await fetch(
        "http://localhost:8080/user/signup-contributor",
        {
            method: "POST",
            headers:{"Content-Type": "application/json"},
            body: JSON.stringify(data),
        }
    );


    if (!response.ok) {
        const errorData = await response.json();
        console.log("Backend error response:", errorData); 

        throw errorData;
    }

    return response; 
}
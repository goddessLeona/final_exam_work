
import { apiFetch } from "../api-fetch";
import { handleResponse } from "../handleResponse";

//####### Member general info 
export async function getGeneralInfo(): Promise<memberResponse> {

    const response =  await apiFetch(
        
        `${process.env.NEXT_PUBLIC_API_URL}/user/username`, { 
            credentials: "include",
    })
            
    return handleResponse(response);
}

export interface memberResponse {
    username: string;
}
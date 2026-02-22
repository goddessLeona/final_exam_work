
export interface WelcomeResponse {
    contributor: boolean;
    message: string;
}

export async function getWelcomeMessage() : Promise<WelcomeResponse>{

    const res = await fetch("http://localhost:8080/contributor/welcome", {
        credentials: "include",
    });

    if(!res.ok){
        throw new Error("Unautorized")
    }

    return res.json();
}


export interface ContributorMeResponse {
    username: string;
    yearSignedUp: number;
    contributor: boolean;
    consentStatus: string | null;
    countPhotoAlbums: number | null;
    message: string | null;
}

export async function getContributorInfo() : Promise<ContributorMeResponse>{

    const res = await fetch("http://localhost:8080/contributor/info", { 
            credentials: "include",
        });

    if(!res.ok){
        throw new Error("Unauthorized");
    }    

    return res.json();
            
}
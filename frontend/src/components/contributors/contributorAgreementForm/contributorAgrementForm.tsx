"use client"

import { useEffect, useState } from "react";
import { Inter, Finger_Paint } from "next/font/google"
import styles from "./contributorAgrementForm.module.css"
import { ContributorFormResponse, getContributorAgreementForm } from "@/lib/api/contributors/contributor-consent-form";
import { postContributorAgreementForm } from "@/lib/api/contributors/contributor-consent-form";


const inter = Inter({
        subsets: ["latin"],
        weight: ["400"]
    });

const fingerPaint = Finger_Paint({
    subsets: ["latin"],
    weight: "400",
}); 

const initialFormState = {
    idCard: null as File | null,
    idCardFace: null as File | null,
    fffFace: null as File | null,
    agree: false,
};

export default function ContributorAgrementForm({
        onApproved,
    }: {
        onApproved?: () => void;
    }) {

    const [formData, setFormData] = useState(initialFormState);
    const [serverData, setServerData] = useState<ContributorFormResponse | null>(null);
    const [error, setError] = useState<Record<string, string>>({});
    const [loading, setLoading] = useState(false);
    const [success, setSuccess] = useState("");

    
    const isApproved = (status?: string | null) => status === "APPROVED";
    const isPending = (status?: string | null) => status === "PENDING";
    
    const isFormPending = isPending(serverData?.consentFormStatus);

    const isIdCardApproved = isApproved(serverData?.idCardReviewed);
    const isIdFaceApproved =isApproved(serverData?.idFaceReviewed);
    const isFffApproved = isApproved(serverData?.facefffReviewed);

    const isContributorApproved = serverData?.consentFormStatus === "APPROVED";


    useEffect(() => {
        async function load() {
            try {
                const data = await getContributorAgreementForm();
                setServerData(data);
                setError({});
            }catch (err: any) {

                if (err.message === "Unauthorized") return ;
                
                if (err.error) {
                    setError(err.error);
                }else {
                    setError({ general: err.message || "Error loading form" });
                }
            }
        }

        load();
    }, []);

    const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    setLoading(true);
    setError({});
    setSuccess("");

    const data = new FormData();
    if (formData.idCard) data.append("idCardFile", formData.idCard);
    if (formData.idCardFace) data.append("idFaceFile", formData.idCardFace);
    if (formData.fffFace) data.append("facefffFile", formData.fffFace);
    data.append("approvedRules", String(formData.agree));

    try {
        const response = await postContributorAgreementForm(data);
        setServerData(response);
        setSuccess("Submitted successfully!");

        if (response.consentFormStatus === "APPROVED") {
            onApproved?.();
        }

    } catch (err: any) {
        
        if (err.errors) {
            setError(err.errors);
        }else {
            setError({ general: err.message || "Error submitting form" })
        }
    } finally {
        setLoading(false);
    }
};    

const generalErrorMessage = error.general || (["idCardFile", "idFaceFile", "facefffFile", "approvedRules"].some(key => error[key]) ? "Missing document" : "");

    return(

        <main className={styles.container}>
            <form onSubmit={handleSubmit}>

                <div className={styles.formBox}>

                    <p className= {`${fingerPaint.className} ${styles.title}`}>Contributor agrement form</p>
                    
                    <div className={styles.field}>
                        <p>You have to upload a photo from your id-card
                            This way we will be able to see that you are over 18 years old</p>
                        <label htmlFor="idCard">Id-card</label>
                        <input
                        id="idCard"
                        name="idCardFile"
                        type="file"
                        accept="image/*"
                        disabled={isFormPending || isIdCardApproved}
                        onChange={(e) =>
                            setFormData({...formData, idCard: e.target.files?.[0] ?? null})
                        }    
                    />
                    {serverData && serverData.consentFormStatus !== "NOT_SUBMITTED" && (
                        <p>Status: {(serverData.idCardReviewed)}</p>
                    )}
                    {error.idCardFile && (<p className={styles.error}>{error.idCardFile}</p>)}

                        {serverData?.idCardReviewed === "REJECTED" && (
                        <div className={styles.rejectMessage}>
                            <p className={styles.rejectTitle}>Why document was rejected:</p>
                            <p className={styles.rejectText}>{serverData?.idCardMessage}</p>
                        </div>
                        )}

                    </div>

                    
                    <div className={styles.field}>
                        <p>You have to upload a photo from you holding up your id-card net to your face
                            This way we will know it is your id-card</p>
                        <label htmlFor="idCardFace">Id-card + Face</label>
                        <input
                        id="idCardFace"
                        name="idFaceFile"
                        type="file"
                        accept="image/*"
                        disabled={isFormPending || isIdFaceApproved}
                        onChange={(e) =>
                            setFormData({...formData, idCardFace: e.target.files?.[0] ?? null})
                        }  
                    />
                     {serverData && serverData.consentFormStatus !== "NOT_SUBMITTED" && (
                        <p>Status: {(serverData.idFaceReviewed)}</p>
                    )}
                    {error.idFaceFile && (<p className={styles.error}>{error.idFaceFile}</p>)}

                        {serverData?.idFaceReviewed === "REJECTED" && (
                        <div className={styles.rejectMessage}>
                            <p className={styles.rejectTitle}>Why document was rejected:</p>
                            <p className={styles.rejectText}>{serverData?.idFaceMessage}</p>
                        </div>
                        )}

                    </div>

                    <div className={styles.field}>
                        <p>You have to upload a photo from you holding up a paper saying FFF and current date. 
                            This way we will know you are the person on the id-card and not just a ex of some one</p>
                        <label htmlFor="fffFace">Identy and FFF</label>
                        <input
                        id="fffFace"
                        name="facefffFile"
                        type="file"
                        accept="image/*"
                        disabled={isFormPending || isFffApproved}
                        onChange={(e) =>
                            setFormData({...formData, fffFace: e.target.files?.[0] ?? null})
                        }  
                        
                    />
                    {serverData && serverData.consentFormStatus !== "NOT_SUBMITTED" && (
                        <p>Status: {(serverData.facefffReviewed)}</p>
                    )}
                    {error.facefffFile && (<p className= {styles.error}> {error.facefffFile} </p>)}

                        {serverData?.facefffReviewed === "REJECTED" && (
                        <div className={styles.rejectMessage}>
                            <p className={styles.rejectTitle}>Why document was rejected:</p>
                            <p className={styles.rejectText}>{serverData?.facefffMessage}</p>
                        </div>
                        )}

                    </div>

                    <div className={styles.field}>
                        <p>Please read our rules and agree</p>
                        <label htmlFor="agree">Agree to rules</label>
                        <input
                        id="agree"
                        name="agree"
                        type="checkbox"
                        disabled= {serverData?.approvedRules === true}
                        checked={formData.agree}
                        onChange={(e) =>
                            setFormData({ ...formData, agree: e.target.checked })
                        }
                    />
                    {error.approvedRules && (<p> {error.approvedRules} </p>)}
                    </div>

                </div>

                {error.general && (
                    <p className={styles.error}>{error.general}</p>
                )}
                {generalErrorMessage && <p className={styles.error}>{generalErrorMessage}</p>}

                <button
                    className={styles.btn}
                    type = "submit"
                    disabled={isFormPending || loading || (!formData.agree && !serverData?.approvedRules)}
                >
                    Submit    
                </button>  

                {success && <p>{success}</p>}

                {serverData && (
                    <div>
                        <p>Form status: {serverData.consentFormStatus}</p>
                    </div>
                )}  

            </form>

        </main>
        
    )

}


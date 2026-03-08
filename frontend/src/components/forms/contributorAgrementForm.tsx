"use client"

import { useEffect, useState } from "react";
import styles from "./contributorAgrementForm.module.css"
import { ContributorFormResponse, getContributorAgreementForm } from "@/lib/api/contributor";
import { postContributorAgreementForm } from "@/lib/api/contributor";

const initialFormState = {
    idCard: null as File | null,
    idCardFace: null as File | null,
    fffFace: null as File | null,
    agree: false,
};

export default function ContributorAgrementForm() {

    const [formData, setFormData] = useState(initialFormState);
    const [serverData, setServerData] = useState<ContributorFormResponse | null>(null);
    const [error, setError] = useState<Record<string, string>>({});
    const [loading, setLoading] = useState(false);
    const [success, setSuccess] = useState("");

    type ReviewStatus = "PENDING" | "APPROVED" | "REJECTED";

    const isFormPending = serverData?.consentStatus === "PENDING";
    const isIdCardApproved = serverData?.idCardReviewed === true;
    const isIdFaceApproved = serverData?.idFaceReviewed === true;
    const isFffApproved = serverData?.facefffReviewed === true;

    useEffect(() => {
        async function load() {
            try {
                const data = await getContributorAgreementForm();
                setServerData(data);
            }catch (err) {
                console.error("No existing form");
            }
        }

        load();
    }, []);

    function getReviewLabel(value: boolean | null): ReviewStatus {
        if (value === null) return "PENDING";
        if (value === true) return "APPROVED";
        return "REJECTED";
    }

    const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    setLoading(true);
    setError({});
    setSuccess("");

    const data = new FormData();
    const updated = await getContributorAgreementForm();

    if (formData.idCard)
        data.append("idCardFile", formData.idCard);

    if (formData.idCardFace)
        data.append("idFaceFile", formData.idCardFace);

    if (formData.fffFace)
        data.append("facefffFile", formData.fffFace);

    data.append("approvedRules", String(formData.agree));

    try {
        await postContributorAgreementForm(data);
        setServerData(updated);
        setSuccess("Submitted successfully!");
    } catch (err: any) {
        setError({ general: err.message || "Error submitting form" });
    }finally {
        setLoading(false);
    }
    
};

    
    return(

        <main className={styles.container}>
            <form onSubmit={handleSubmit}>

                <div className={styles.formBox}>
                    <h1>Contributor agrement form</h1>

                    <div className={styles.field}>
                        <p>You have to upload a photo from your id-card</p>
                        <p>This way we will be able to see that you are over 18 years old</p>
                        <label htmlFor="idCard">Id-card</label>
                        <input
                        id="idCard"
                        name="idCard"
                        type="file"
                        accept="image/*"
                        disabled={isFormPending || isIdCardApproved}
                        onChange={(e) =>
                            setFormData({...formData, idCard: e.target.files?.[0] ?? null})
                        }    
                    />
                    {serverData && (
                        <p>Status: {getReviewLabel(serverData.idCardReviewed)}</p>
                    )}
                    {error.idCard && (<p className={styles.error}>{error.idCard}</p>)}
                    </div>

                    
                    <div className={styles.field}>
                        <p>You have to upload a photo from you holding up your id-card net to your face</p>
                        <p>This way we will know it is your id-card</p>
                        <label htmlFor="idCardFace">Id-card</label>
                        <input
                        id="idCardFace"
                        name="idCardFace"
                        type="file"
                        accept="image/*"
                        disabled={isFormPending || isIdFaceApproved}
                        onChange={(e) =>
                            setFormData({...formData, idCardFace: e.target.files?.[0] ?? null})
                        }  
                    />
                     {serverData && (
                        <p>Status: {getReviewLabel(serverData.idFaceReviewed)}</p>
                    )}
                    {error.idCardFace && (<p className={styles.error}>{error.idCardFace}</p>)}
                    </div>

                    <div className={styles.field}>
                        <p>You have to upload a photo from you holding up a paper saying FFF</p>
                        <p>This way we will know you are the person on the id-card and not just a ex of some one</p>
                        <label htmlFor="fffFace">Id-card</label>
                        <input
                        id="fffFace"
                        name="fffFace"
                        type="file"
                        accept="image/*"
                        disabled={isFormPending || isFffApproved}
                        onChange={(e) =>
                            setFormData({...formData, fffFace: e.target.files?.[0] ?? null})
                        }  
                        
                    />
                    {serverData && (
                        <p>Status: {getReviewLabel(serverData.facefffReviewed)}</p>
                    )}
                    {error.fffFace && (<p className={styles.error}>{error.fffFace}</p>)}
                    </div>

                    <div className={styles.field}>
                        <p>Please read our rules and agree</p>
                        <label htmlFor="agree">Agree to rules</label>
                        <input
                        id="agree"
                        name="agree"
                        type="checkbox"
                        checked={formData.agree}
                        onChange={(e) =>
                            setFormData({ ...formData, agree: e.target.checked })
                        }
                    />
                    {error.agree && (<p className={styles.error}>{error.agree}</p>)}
                    </div>

                </div>

                <button
                    type = "submit"
                    disabled={isFormPending || loading || !formData.agree}
                >
                    Submit    
                </button>  

                {success && <p>{success}</p>}

                {serverData && (
                    <div>
                        <p>Form status: {serverData.consentStatus}</p>
                    </div>
                )}  

            </form>

        </main>

    )
    
}
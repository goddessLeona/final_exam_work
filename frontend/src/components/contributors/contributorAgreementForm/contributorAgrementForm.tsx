"use client"

import { useEffect, useState } from "react";
import { handleAuthError } from "@/lib/auth/handleAuthError";
import { Inter, Finger_Paint } from "next/font/google"

import { ContributorFormResponse, getContributorAgreementForm } from "@/lib/api/contributors/contributor-consent-form";
import { postContributorAgreementForm } from "@/lib/api/contributors/contributor-consent-form";
import { ConsentFormStatus } from "@/types/consent-form-status";
import { ReviewStatus } from "@/types/reviewStatus";
import styles from "./contributorAgrementForm.module.css"


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

    const formStatusLabels: Record<ConsentFormStatus, string> = {
        NOT_SUBMITTED: "Not submitted",
        PENDING: "Under review",
        APPROVED: "Approved",
        REJECTED: "Rejected",
    };

    const reviewStatusLabels: Record<ReviewStatus, string> = {
        NOT_SUBMITTED: "Not submitted", 
        PENDING: "Under review",
        APPROVED: "Approved",
        REJECTED:"Rejected",
    }

    useEffect(() => {
        async function load() {
            try {
                const data = await getContributorAgreementForm();
                setServerData(data);
                setError({});
            }catch (err: any) {

                if (handleAuthError(err)) return;

                setError(
                    err?.errors
                        ? err.errors
                        : { general: err?.message || "Error loading form" }
                );
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
                    
                    <div className={`${styles.field} ${
                            isIdCardApproved ? styles.fieldLocked : ""
                        }`}
                    >
                        <p className={styles.rejectTitle}>You have to upload a photo from your id-card
                            This way we will be able to see that you are over 18 years old
                        </p>

                        <label
                            htmlFor="idCard"
                            className={`${styles.fileLabel} ${
                                isIdCardApproved ? styles.fileLabelLocked : ""
                            }`}
                            >Id-card
                        </label>

                        
                        {isIdCardApproved && (
                            <div className={styles.lockedTag}>
                                document was approved by admin
                            </div>
                        )}
                        

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

                        {formData.idCard && (
                            <p className={styles.fileName}>
                                Selected: {formData.idCard.name}
                            </p>
                        )}

                        <div>
                            {serverData?.idCardReviewed && (
                                <p className={styles.statusR}>{ reviewStatusLabels[serverData.idCardReviewed]}</p>
                            )}
                        </div>

                        {error.idCardFile && (<p className={styles.error}>{error.idCardFile}</p>)}

                        {serverData?.idCardReviewed === "REJECTED" && (
                            <div className={styles.rejectMessage}>
                                <p className={styles.rejectTitle}>Why document was rejected:</p>
                                <p className={styles.rejectText}>{serverData?.idCardMessage}</p>
                            </div>
                        )}

                    </div>

                    
                    <div className={`${styles.field} ${
                            isIdFaceApproved ? styles.fieldLocked : ""
                        }`}
                    >
                        <p className={styles.rejectTitle}>You have to upload a photo from you holding up your id-card net to your face
                            This way we will know it is your id-card
                        </p>

                        <label htmlFor="idCardFace"
                            className={`${styles.fileLabel} ${
                                isIdFaceApproved ? styles.fileLabelLocked : ""
                            }`}
                            >Id-card + Face
                        </label>

                        {isIdFaceApproved && (
                            <div className={styles.lockedTag}>
                                document was approved by admin
                            </div>
                        )}

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

                            {formData.idCardFace && (
                                <p className={styles.fileName}>
                                    Selected: {formData.idCardFace.name}
                                </p>
                            )}

                            <div>
                                {serverData?.idFaceReviewed && (
                                    <p className={styles.statusR}>
                                        {reviewStatusLabels[serverData.idFaceReviewed]}
                                    </p>
                                )}
                            </div>

                            {error.idFaceFile && (<p className={styles.error}>{error.idFaceFile}</p>)}

                            {serverData?.idFaceReviewed === "REJECTED" && (
                                <div className={styles.rejectMessage}>
                                    <p className={styles.rejectTitle}>Why document was rejected:</p>
                                    <p className={styles.rejectText}>{serverData?.idFaceMessage}</p>
                                </div>
                            )}

                    </div>

                    <div className={`${styles.field} ${
                            isFffApproved ? styles.fieldLocked : ""
                        }`}
                    >
                        <p className={styles.rejectTitle}>You have to upload a photo from you holding up a paper saying FFF and current date. 
                            This way we will know you are the person on the id-card and not just a ex of some one
                        </p>

                        <label htmlFor="fffFace" className={styles.fileLabel}>Identy and FFF</label>

                        {isFffApproved && (
                            <div className={styles.lockedTag}>
                                document was approved by admin
                            </div>
                        )}

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

                            {formData.fffFace && (
                                <p className={styles.fileName}>
                                    Selected: {formData.fffFace.name}
                                </p>
                            )}

                            <div>
                                {serverData?.facefffReviewed && (
                                    <p className={styles.statusR}>
                                        {reviewStatusLabels[serverData.facefffReviewed]}
                                    </p>
                                )}
                            </div>
                            
                            {error.facefffFile && (<p className= {styles.error}> {error.facefffFile} </p>)}

                            {serverData?.facefffReviewed === "REJECTED" && (
                                <div className={styles.rejectMessage}>
                                    <p className={styles.rejectTitle}>Why document was rejected:</p>
                                    <p className={styles.rejectText}>{serverData?.facefffMessage}</p>
                                </div>
                            )}

                    </div>

                    <div className={styles.field}>
                        <p className={styles.rejectTitle}>Please read our rules and agree</p>
                        <label htmlFor="agree" className={styles.rejectTitle}>Agree to rules</label>
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

                    {error.general && (
                        <p className={styles.error}>{error.general}</p>
                    )}

                    {generalErrorMessage && <p className={styles.error}>{generalErrorMessage}</p>}

                    <button
                        className={styles.btn}
                        type = "submit"
                       
                    >
                        Submit    
                    </button>  

                    {success && <p>{success}</p>}

                    {serverData && (
                        <div className={styles.statusBox}>
                            <span className={styles.statusBadge}>
                                    {serverData.consentFormStatus
                                    ? formStatusLabels[serverData.consentFormStatus]
                                    : "No status"}
                            </span>
                        </div>
                    )}

                </div>

            </form>

        </main>
        
    )

}


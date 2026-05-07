"use client"

import { useEffect, useState } from "react";
import { useParams } from "next/navigation";
import { getConsentFormData, ConsentFormDataResponse, adminConsentFormResponse } from "@/lib/api/admin";
import styles from "./admin-getFormData.module.css"

export default function ConsentData () {

    const param = useParams();
    const id = param.id as string;
    
    const [data, setData] = useState<ConsentFormDataResponse | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    const [rejectOpen, setRejectOpen] = useState(false);
    const [rejectTarget, setRejectTarget] = useState<null | {
        field: "idCardStatus" | "idFaceStatus" | "facefffStatus";
        messageField: "idCardMessage" | "idFaceMessage" | "facefffMessage";
    }>(null);
    const [rejectMessage, setRejectMessage] = useState("");

    const isIdCardFinal = data?.documentIdCard.status === "APPROVED" || data?.documentIdCard.status === "REJECTED"
    const isIdFaceFinal = data?.documentIdCardFace.status === "APPROVED" || data?.documentIdCardFace.status === "REJECTED"
    const isFaceFFFFinal = data?.documentFaceFFF.status === "APPROVED" || data?.documentFaceFFF.status === "REJECTED"
    
    async function handleReview(
        field: "idCardStatus" | "idFaceStatus" | "facefffStatus",
        messageField: "idCardMessage" | "idFaceMessage" | "facefffMessage",
        status: "APPROVED" | "REJECTED",
        messageText?: string
    ) {
        try {
            await adminConsentFormResponse(id, {
                [field]: status,
                [messageField]: messageText ?? ""
            });

            const updated = await getConsentFormData(id);
            setData(updated);
        }catch(err: any) {
            setError(err.message || "Failed to update document");
        }
    }

    useEffect (() => {
        async function fetchConsentData() {
            try {
                const result = await getConsentFormData(id);
                setData (result)
            }catch (err :any) {
                console.error("Data error :" , err);
                setError( err.message || "Failed to load any consentform data");
            }finally {
                setLoading(false);
            }
        }

        if (id) {
            fetchConsentData();
        }

    },[id]);

    if (loading) {
        return <div className={styles.container}>Loading...</div>;
    }

    if (error) {
        return <div className={styles.container}>{error}</div>;
    }

    if(!data) {
        return <div className={styles.container}>No data</div>
    }

    return (
        <div className={styles.container}>
            <h2>{data.username}</h2>

            <div className={styles.section}>

                <div>
                    <div className={styles.documents}>
                        <div>
                            <img src={`http://localhost:8080/admin/consent/${id}/document/id-card`} width={200} />
                            <p>ID-Card status: </p>
                        </div> 

                        {isIdCardFinal ? (
                            <p className={styles.finalStatus}>
                                 {data.documentIdCard.status}
                            </p> 
                        ) :
                        (
                            <div className={styles.btnSeection}>
                                <button className={styles.button} onClick={() => handleReview ("idCardStatus", "idCardMessage", "APPROVED", "")} disabled={isIdCardFinal}>Approve</button>
                                <button className={styles.button}
                                 onClick={() => {
                                    setRejectTarget ({ 
                                        field: "idCardStatus",
                                        messageField: "idCardMessage"
                                    }); 
                                    setRejectOpen(true);
                                }}
                                disabled={isIdCardFinal} 
                                >Reject
                                </button>
                            </div>  
                        )}     
                    </div>    
                </div>

                <div>
                    <div className={styles.documents}>
                        <div>
                            <img src={`http://localhost:8080/admin/consent/${id}/document/id-face`} width={200} />
                            <p>ID-Face status: </p>
                        </div>

                        {isIdFaceFinal ? (
                            <p className={styles.finalStatus}>
                                 {data.documentIdCardFace.status}
                            </p> 
                        ) :
                        (
                        <div className={styles.btnSeection}>
                            <button className={styles.button} onClick={() => handleReview ("idFaceStatus", "idFaceMessage", "APPROVED")}>Approve</button>
                            <button className={styles.button}
                             onClick={() => {setRejectTarget({
                                field: "idFaceStatus",
                                messageField: "idFaceMessage"
                            });
                            setRejectOpen(true);
                            }}
                            disabled={isIdFaceFinal} 
                            >Reject</button>
                        </div>  
                        )}  
                    </div>
                    
                </div>

                <div>
                    <div className={styles.documents}>
                        <div>
                            <img src={`http://localhost:8080/admin/consent/${id}/document/face-fff`} width={200} />
                            <p>FFF + Face status : </p>
                        </div>

                        {isFaceFFFFinal ? (
                            <p className={styles.finalStatus}>
                                 {data.documentFaceFFF.status}
                            </p> 
                        ) :
                        (
                        <div className={styles.btnSeection}>
                            <button className={styles.button} onClick={() => handleReview ("facefffStatus", "facefffMessage", "APPROVED")}>Approve</button>
                            <button className={styles.button} 
                            onClick={() => {setRejectTarget ({
                                field: "facefffStatus",
                                messageField: "facefffMessage"
                            });
                            setRejectOpen(true);
                            }}
                            disabled={isFaceFFFFinal} 
                            >Reject</button>
                        </div>   
                        )} 
                    </div> 
                </div>
                
            </div> 

                {rejectOpen && (
                <div className={styles.modalOverlay}>
                    <div className={styles.modal}>

                        <h3>Reject document</h3>

                        <textarea
                            value={rejectMessage}
                            onChange={(e) =>
                                setRejectMessage(e.target.value)
                            }
                            placeholder="Write reason for rejection..."
                        />

                        <div className={styles.modalButtons}>

                            <button
                                onClick={() => {
                                    setRejectOpen(false);
                                    setRejectMessage("");
                                }}
                            >
                                Cancel
                            </button>

                            <button
                                onClick={async () => {

                                    if (!rejectTarget) return;

                                    if (rejectMessage.trim() === "") {
                                        setError("Please provide a reason");
                                        return;
                                    }

                                    await handleReview(
                                        rejectTarget.field,
                                        rejectTarget.messageField,
                                        "REJECTED",
                                        rejectMessage
                                    );

                                    setRejectOpen(false);
                                    setRejectMessage("");
                                    setRejectTarget(null);
                                }}
                            >
                                Confirm Reject
                            </button>

                        </div>
                    </div>
                </div>
            )}
        </div>
    )    
     
}


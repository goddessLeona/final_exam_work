"use client"

import { useEffect, useState } from "react";
import { useParams } from "next/navigation";
import { getConsentFormData, ConsentFormDataResponse } from "@/lib/api/admin";
import styles from "./admin-getFormData.module.css"

export default function ConsentData () {

    const param = useParams();
    const id = param.id as string;

    const [data, setData] = useState<ConsentFormDataResponse | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

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
                            <p>{data.documentIdCard.status}</p>
                        </div> 

                        <div className={styles.btnSeection}>
                            <button className={styles.button}>Approve</button>
                            <button className={styles.button}>Reject</button>
                        </div>   
                    </div>    
                </div>

                <div>
                    <div className={styles.documents}>
                        <div>
                            <img src={`http://localhost:8080/admin/consent/${id}/document/id-face`} width={200} />
                            <p>ID-Face status: </p>
                            <p>{data.documentIdCard.status}</p>
                        </div>
                        <div className={styles.btnSeection}>
                            <button className={styles.button}>Approve</button>
                            <button className={styles.button}>Reject</button>
                        </div>    
                    </div>
                    
                </div>

                <div>
                    <div className={styles.documents}>
                        <div>
                            <img src={`http://localhost:8080/admin/consent/${id}/document/face-fff`} width={200} />
                            <p>FFF + Face status : </p>
                            <p>{data.documentIdCard.status}</p>
                        </div>
                        <div className={styles.btnSeection}>
                            <button className={styles.button}>Approve</button>
                            <button className={styles.button}>Reject</button>
                        </div>    
                    </div> 
                </div>
                
            </div> 

        </div>
    )
}
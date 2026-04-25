"use client"

import { useEffect, useState } from "react";
import { getAdminDashboard, AdminDashboardResponse } from "@/lib/api/admin";
import ConsentData from "./admin-getFormData";
import Link from "next/link";
import styles from "./admin-dashboard.module.css"

export default function AdminDashboard () {

    const [data, setData] = useState<AdminDashboardResponse | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        async function fetchDashboard() {
            try {
                const result = await getAdminDashboard();
                setData(result);
            } catch(err : any) {
                console.error("Dashboard error:", err);
                setError(err.message || "Failed to load dashboard");
            } finally {
                setLoading(false);
            }
        }

        fetchDashboard();
    },[]);

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
            <h2>Dashboard for Consent Forms</h2>
            <h3>Total amount of registerd forms: ({data.total})</h3>

            <div className={styles.section}>
                <h3>Pending ({data.pending.total})</h3>
                <ul>
                    {data.pending.latest.map((item, index) => (
                        <li key={index}>
                            <Link href={`/admin/consent/${item.consentFormId}`}>
                                <div className={styles.list}>
                                    <p>{item.username}</p>
                                    <p>Pending : ({item.documentsPending})</p>
                                    <p>Approved : ({item.documentsApproved})</p>
                                    <p>Rejected : ({item.documentsRejected})</p>
                                </div>
                            </Link>        
                        </li>
                    ))}
                </ul>
            </div> 

            <div className={styles.section}>        
                <h3>Rejected ({data.rejected.total})</h3>
                <ul>
                    {data.rejected.latest.map((item, index) => (
                        <li key={index}>
                            <p>{item.username}</p>
                            Pending : ({item.documentsPending})
                            Approved : ({item.documentsApproved})
                            Rejected : ({item.documentsRejected})
                        </li>
                    ))}
                </ul>
            </div>  

            <div className={styles.section}>  
                <h3>Approved ({data.approved.total})</h3>
                <ul>
                    {data.approved.latest.map((item, index) => (
                        <li key={index}>
                            <p>{item.username}</p>
                            Approved : ({item.documentsApproved})
                        </li>
                    ))}
                </ul>
            </div>    

               

            {/*   not used right now     
            <div className={styles.section}>
                <h3>Not submitted ({data.notSubmitted.total})</h3>
                <ul>
                    {data.notSubmitted.latest.map((item, index) => (
                        <li key={index}>
                            <p>{item.username}</p>
                            Pending : {item.documentsPending}
                            Approved : {item.documentsApproved}
                            Rejected : {item.documentsRejected}
                        </li>
                    ))}
                </ul>
            </div> 
            */}

        </div>
    );   

}
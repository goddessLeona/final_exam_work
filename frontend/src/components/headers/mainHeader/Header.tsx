import Image from "next/image";
import styles from "./header.module.css";

import { Inter, Finger_Paint } from "next/font/google"

const inter = Inter({
        subsets: ["latin"],
        weight: ["600"]
    });

const fingerPaint = Finger_Paint({
    subsets: ["latin"],
    weight: "400",
}); 

export default function Header(){
    return(
        <header className={styles.header}>

            <div className={styles.branchRight}>
                <Image
                    src="/branch-png.webp"
                    alt="branch"
                    width={200}
                    height={200}
                    priority
                />
            </div>

            <div className={styles.branchLeft}>
                <Image
                    src="/branch-png.webp"
                    alt="branch left"
                    width={200}
                    height={200}
                    priority
                />
            </div>
            
            <div className={styles.centerContent}>
                
                <div className={styles.headerText}>
                    <p className={`${fingerPaint.className} ${styles.title}`}>Examen work 2026 - Content platform</p>
                    <p className={`${fingerPaint.className} ${styles.title}`}> Fullstack-developer</p>
                    <p className={`${fingerPaint.className} ${styles.title}`}>Petra Johansson</p>
                    <p>version 0.01 no styling just function</p>
                </div>
                
            </div>
        </header>
    );
    
}
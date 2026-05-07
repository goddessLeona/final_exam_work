import Image from "next/image";
import styles from "./header.module.css";

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
                    <h1>Examen work 2026 - Content platform</h1>
                    <h1> Fullstack-developer</h1>
                    <h1>Petra Johansson</h1>
                    <p>version 0.01 no styling just function</p>
                </div>
            </div>
        </header>
    );
    
}
import Image from "next/image";
import styles from "./Header.module.css";

export default function Header(){
    return(
        <header className={styles.header}>

            <div className={styles.branchRight}>
                <Image
                    src="/branch-png.webp"
                    alt="branch"
                    width={200}
                    height={200}
                />
            </div>

            <div className={styles.branchLeft}>
                <Image
                    src="/branch-png.webp"
                    alt="branch left"
                    width={200}
                    height={200}
                />
            </div>
            
            <div className={styles.centerContent}>
                <div className={styles.headerImage}> 
                <Image className={styles.image}
                    src="/me.jpg"
                    alt="me"
                    width={150}
                    height={150}
                />
                </div>

                <div className={styles.headerText}>
                    <h1>Examen work</h1>
                    <h1> Fullstack</h1>
                    <p>version 0.01 no styling just function</p>
                </div>
            </div>
        </header>
    );
    
}
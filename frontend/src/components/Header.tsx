import Image from "next/image";
import styles from "./Header.module.css";

export default function Header(){
    return(
        <header className={styles.header}>
            <div className={styles.headerImage}> 
            <Image src="/headerV1.png" alt="Logo" width={1003} height={226} />
            </div> 
        </header>
    );
}
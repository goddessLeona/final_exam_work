import Link from "next/link";
import styles from "./footer.module.css";

export default function Footer(){
    return(
        <footer className={styles.footer}>
            <p>Petra johansson </p>
            <Link
            href="https://github.com/goddessLeona"
            target="_blank"
            rel = "noopener noreferrer"
            className={styles.link}
            >GitHub</Link>
        </footer>
    );
}
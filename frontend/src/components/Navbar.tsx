import Link from "next/link";
import styles from "./navbar.module.css";

export default function Navbar() {
  return (
    <nav className={styles.nav}>
      <div className={styles.left}>
        <Link className={styles.link} href="/">Home</Link>
        <Link className={styles.link} href="/subscribe-contributor">Become a contributor</Link>
      </div>

      <div className={styles.right}>
        <Link className={styles.link} href="/signup">Sign up</Link>
        <Link className={styles.link} href="/login">Login</Link>
      </div>
    </nav>
  );
}


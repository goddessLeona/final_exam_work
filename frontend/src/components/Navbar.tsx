import styles from "./Navbar.module.css";

export default function Navbar() {
  return (
    <nav className={styles.nav}>
      <a className={styles.link} href="/">Home</a>
      <a className={styles.link} href="/contributor">Becoma a contributor</a>
      <a className={styles.link} href="/login">Login</a>
    </nav>
  );
}

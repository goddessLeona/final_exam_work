import styles from "./Navbar.module.css";

export default function Navbar() {
  return (
    <nav className={styles.nav}>
      <div className={styles.left}>
        <a className={styles.link} href="/">Home</a>
        <a className={styles.link} href="/subscribe.contributor">Become a contributor</a>
      </div>

      <div className={styles.right}>
        <a className={styles.link} href="/signup">Sign up</a>
        <a className={styles.link} href="/login">Login</a>
      </div>
    </nav>
  );
}


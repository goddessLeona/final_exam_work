import Link from "next/link";
import styles from "./navbar-admin.module.css"

import LogoutButton from "../button/logout-buton";

export default function NavbarAdmin() {
  return (
    <nav className={styles.nav}>
      <div className={styles.left}>
        <Link className={styles.link} href="/contributor">Home</Link>
        <Link className={styles.link} href="">User info</Link>
        <Link className={styles.link} href="">Stats</Link>
        <Link className={styles.link} href="">more?</Link>
      </div>
      <div className={styles.right}>
        <Link className={styles.link} href="">Settings</Link>
        <div className={styles.right}>
        <LogoutButton />
      </div>
      </div>
    </nav>
  );
}
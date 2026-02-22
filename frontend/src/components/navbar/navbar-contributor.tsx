import Link from "next/link";
import styles from "./navbar-contributor.module.css";

import LogoutButton from "../button/logout-buton";

export default function NavbarContributor() {
  return (
    <nav className={styles.nav}>
      <div className={styles.left}>
        <Link className={styles.link} href="/contributor">Home</Link>
        <Link className={styles.link} href="">Profile</Link>
        <Link className={styles.link} href="">Photo</Link>
        <Link className={styles.link} href="">Messages</Link>
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
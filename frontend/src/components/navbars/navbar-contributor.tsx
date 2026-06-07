import Link from "next/link";
import styles from "./navbar-contributor.module.css";

import LogoutButton from "../button/logout-button";

export default function NavbarContributor() {
  return (
    <nav className={styles.nav}>

      <div className={styles.left}>
        <Link className={styles.link} href="/contributor">Home</Link>
      </div>

      <div className={styles.right}>
        <Link className={styles.link} href="/member">Member Page</Link>
        <LogoutButton />
      </div>
      
    </nav>
  );
}
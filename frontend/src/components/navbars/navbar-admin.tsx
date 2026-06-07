import Link from "next/link";
import styles from "./navbar-admin.module.css"

import LogoutButton from "../button/logout-button";

export default function NavbarAdmin() {
  return (
    <nav className={styles.nav}>
      <div className={styles.left}>
        <Link className={styles.link} href="/admin">Home</Link>
      </div>
      <div className={styles.right}>
        <Link className={styles.link} href="/member">MemberPage</Link>
        <div className={styles.right}>
        <LogoutButton />
      </div>
      </div>
    </nav>
  );
}
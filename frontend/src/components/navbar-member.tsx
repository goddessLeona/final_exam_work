import Link from "next/link";
import styles from "./navbar-member.module.css";
import LogoutButton from "./logout-buton";

export default function NavbarMember() {
  return (
    <nav className={styles.nav}>
      <div className={styles.left}>
        <Link className={styles.link} href="/member">Home</Link>
        <Link className={styles.link} href="/member/subscribe-contributor">Become a contributor</Link>
      </div>

      <div className={styles.right}>
        <LogoutButton />
      </div>
    </nav>
  );
}

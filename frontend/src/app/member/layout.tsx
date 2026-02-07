import Header from "@/components/header";
import NavbarMember from "@/components/navbar-member";
import Footer from "@/components/footer";

import styles from "./layout.module.css"

export default function MemberLayout({
  children,
  left,
  right,
}: {
  children: React.ReactNode;
  left: React.ReactNode;
  right: React.ReactNode;
}) {
  return (
    <>
        <Header />
        <NavbarMember />

        <main className={styles.memberLayout}>
          <aside className={styles.leftSidebar}>{left}</aside>
          <section className={styles.centerContent}>{children}</section>
          <aside className={styles.rightSidebar}>{right}</aside>
        </main>

        <Footer />
    </>
  );
}

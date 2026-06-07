import Header from "@/components/headers/mainHeader/Header";
import NavbarMember from "@/components/navbars/navbar-member";
import Footer from "@/components/footers/footer";

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

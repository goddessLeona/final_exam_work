import Header from "@/components/headers/mainHeader/Header";
import NavbarAdmin from "@/components/navbars/navbar-admin";
import Footer from "@/components/footers/footer";
import styles from "./layout.module.css"

export default function ComponentLayout({
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
        <NavbarAdmin />

        <main className={styles.componentLayout}>
          <aside className={styles.leftSidebar}>{left}</aside>
          <section className={styles.centerContent}>{children}</section>
          <aside className={styles.rightSidebar}>{right}</aside>
        </main>

        <Footer />
    </>
  );
}
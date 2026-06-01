import Header from "@/components/headers/mainHeader/Header";
import NavbarContributor from "@/components/navbars/navbar-contributor";
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
        <NavbarContributor />

        <main className={styles.componentLayout}>
          <aside className={styles.leftSidebar}>{left}</aside>
          <section className={styles.centerContent}>{children}</section>
          <aside className={styles.rightSidebar}>{right}</aside>
        </main>

        <Footer />
    </>
  );
}
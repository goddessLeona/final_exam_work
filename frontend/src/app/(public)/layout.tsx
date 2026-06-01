import Header from "@/components/headers/mainHeader/Header"
import Navbar from "@/components/navbars/navbar";
import Footer from "@/components/footers/footer";

export default function PublicLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <>
        <Header />
        <Navbar />
        <main>{children}</main>
        <Footer />
    </>
  );
}

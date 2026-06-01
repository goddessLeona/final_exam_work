import Header from "@/components/Header"
import Navbar from "@/components/navbars/navbar";
import Footer from "@/components/footer";

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

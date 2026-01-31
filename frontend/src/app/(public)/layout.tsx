import Header from "@/components/header";
import Navbar from "@/components/navbar";
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

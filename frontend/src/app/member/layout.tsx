import Header from "@/components/header";
import NavbarMember from "@/components/navbar-member";
import Footer from "@/components/footer";

export default function MemberLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <>
        <Header />
        <NavbarMember />
        <main>{children}</main>
        <Footer />
    </>
  );
}

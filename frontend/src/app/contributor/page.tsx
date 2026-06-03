"use client";

import { useEffect, useState } from "react";
import Welcome from "@/components/contributors/wellcomeMessage/welcome-message";
import ContributorAgrementForm from "@/components/contributors/contributorAgreementForm/contributorAgrementForm";
import ContributorDashboard from "@/components/contributors/contributorDashboard/contributorDashboard";
import { getContributorAgreementForm } from "@/lib/api/contributors/contributor-consent-form";
import styles from "@/app/contributor/page.module.css"

type ViewState = "loading" | "form" | "dashboard";

export default function ContributorPage() {
  const [view, setView] = useState<ViewState>("loading");

  useEffect(() => {
    getContributorAgreementForm()
      .then((data) => {
        if (data.consentFormStatus === "APPROVED") {
          setView("dashboard");
        } else {
          setView("form");
        }
      })
      .catch(() => {
        window.location.href = "/login";
      });
  }, []);

  return (
    <main className={styles.main}>
      <Welcome />

      {view === "loading" && <p>Loading...</p>}

      {view === "form" && (
        <ContributorAgrementForm
          onApproved={() => setView("dashboard")}
        />
      )}

      {view === "dashboard" && <ContributorDashboard />}
    </main>
  );
}






